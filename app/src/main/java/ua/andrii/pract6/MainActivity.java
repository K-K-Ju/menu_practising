package ua.andrii.pract6;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    TextInputEditText num1Input, num2Input;
    TextView resultView;
    RecyclerView rvActions;
    Button btnPopup;

    private final int SEPIA = R.style.Theme_Sepia;
    private final int WHITE = R.style.Theme_White;
    private final int NIGHT = R.style.Theme_Night;
    private final String KEY_THEME= "Theme";

    private int currentTheme = WHITE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentTheme = PreferenceManager.getDefaultSharedPreferences(this).getInt(KEY_THEME, WHITE);
        setTheme();
        super.onCreate(savedInstanceState);
        setUpViews();
    }

    private void setUpViews() {
        setContentView(R.layout.activity_main);
        num1Input = findViewById(R.id.num1_input);
        num2Input = findViewById(R.id.num2_input);
        resultView = findViewById(R.id.resultView);
        btnPopup = findViewById(R.id.btn_popup);

        rvActions = findViewById(R.id.rvActions);
        ActionAdapter actionAdapter = new ActionAdapter(new String[]{"+", "-", "*", "/"});
        rvActions.setAdapter(actionAdapter);
        rvActions.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        actionAdapter.notifyDataSetChanged();

        PopupMenu popup = new PopupMenu(this, btnPopup);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            onMenuItemClick(item);
            return true;
        });
        btnPopup.setOnClickListener(v -> popup.show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    void setTheme() {
        setTheme(currentTheme);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        onMenuItemClick(item);
        return super.onOptionsItemSelected(item);
    }

    private void onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op_style_white:
                currentTheme = WHITE;
                setTheme();
                recreate();
                break;
            case R.id.op_style_sepia:
                currentTheme = SEPIA;
                setTheme();
                recreate();
                break;
            case R.id.op_style_night:
                currentTheme = NIGHT;
                setTheme();
                recreate();
                break;
            case R.id.op_font_12:
                setFontSize(12);
                break;
            case R.id.op_font_14:
                setFontSize(14);
                break;
            case R.id.op_font_18:
                setFontSize(18);
                break;
        }
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(KEY_THEME, currentTheme).apply();
    }

    public void performAction(Action action) {
        int num1 = Integer.parseInt(Objects.requireNonNull(num1Input.getText()).toString());
        int num2 = Integer.parseInt(Objects.requireNonNull(num2Input.getText()).toString());
        int result;
        switch (action) {
            case SUB: result = num1 - num2;
                break;
            case MUL: result = num1 * num2;
                break;
            case DIV: result = num1 / num2;
                break;
            default:  result = num1 + num2;
        }

        resultView.setText(String.format(Locale.US, "%d", result));
    }

    private void setFontSize(float fontSize){
        resultView.setTextSize(fontSize);
        num1Input.setTextSize(fontSize);
        num2Input.setTextSize(fontSize);
    }

    public enum Action {
        ADD("+"), SUB("-"), MUL("*"), DIV("/");

        Action(String str) {
            this.str = str;
        }
        final String str;
    }
}
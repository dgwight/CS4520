package dylanwight.madcourse.neu.edu.numad16s_dylanwight.foodGrouper;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dylanwight.madcourse.neu.edu.numad16s_dylanwight.R;

public class AddFoodActivity extends Activity {
    List<Integer> servings = new ArrayList<>();
    List<String> foodGroups = new ArrayList<>();
    List<Button> plusButtons = new ArrayList<>();
    List<Button> minusButtons = new ArrayList<>();
    List<TextView> groupLabels = new ArrayList<>();
    List<Integer> drawables = new ArrayList<>();
    List<View> groupViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_input_food);

        TextView promptText = (TextView) findViewById(R.id.promptText);
        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishActivity();
            }
        });

        this.loadLists();

        Integer i = 0;
        for (View ignored : groupViews) {
            loadView(i);
            updateLabel(i);
            setAddButton(i);
            setMinusButton(i);
            i++;
        }
    }

    private void loadView(Integer group) {
        View groupView = groupViews.get(group);
        groupView.setBackgroundResource(drawables.get(group));
        groupLabels.add((TextView) groupView.findViewById(R.id.groupLabel));
        minusButtons.add((Button) groupView.findViewById(R.id.minusButton));
        plusButtons.add((Button) groupView.findViewById(R.id.plusButton));
    }

    private void updateLabel(Integer group) {
        groupLabels.get(group).setText(foodGroups.get(group) + "\n(" + servings.get(group) + ")");
        if (servings.get(group) == 0) {
            minusButtons.get(group).setVisibility(View.INVISIBLE);
        } else if (minusButtons.get(group).getVisibility() == View.INVISIBLE) {
            minusButtons.get(group).setVisibility(View.VISIBLE);
        }
    }

    private void setAddButton(Integer group) {
        final Integer groupNum = group;
        plusButtons.get(group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servings.set(groupNum, servings.get(groupNum) + 1);
                updateLabel(groupNum);
            }
        });
    }

    private void setMinusButton(Integer group) {
        final Integer groupNum = group;
        minusButtons.get(group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servings.set(groupNum, servings.get(groupNum) - 1);
                updateLabel(groupNum);
            }
        });
    }

    private void loadLists() {
        foodGroups.add("Fruits");
        foodGroups.add("Vegetables");
        foodGroups.add("Grains");
        foodGroups.add("Dairy");
        foodGroups.add("Protein");
        foodGroups.add("Fats");

        servings.add(0);
        servings.add(0);
        servings.add(0);
        servings.add(0);
        servings.add(0);
        servings.add(0);

        groupViews.add(findViewById(R.id.fruits));
        groupViews.add(findViewById(R.id.vegetables));
        groupViews.add(findViewById(R.id.grains));
        groupViews.add(findViewById(R.id.dairy));
        groupViews.add(findViewById(R.id.proteins));
        groupViews.add(findViewById(R.id.fats));

        drawables.add(R.drawable.fruits);
        drawables.add(R.drawable.vegetables);
        drawables.add(R.drawable.grains);
        drawables.add(R.drawable.dairy);
        drawables.add(R.drawable.proteins);
        drawables.add(R.drawable.fats);
    }

    private void finishActivity() {
        FoodEntry newEntry = new FoodEntry(new Date(), servings.get(0), servings.get(1), servings.get(2),
                servings.get(3), servings.get(4), servings.get(5));

        NotificationManager mNotificationManager =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancelAll();

        saveText(newEntry);
        this.finish();
    }

    //http://stackoverflow.com/questions/28755934/android-studio-writing-text-to-a-file-using-a-save-button-in-the-menu
    public void saveText(FoodEntry newEntry) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(openFileOutput("data.txt", MODE_APPEND));
            out.write(newEntry.toString());
            out.write('\n');
            out.close();
        } catch (Throwable t) {
            Log.d("error writing to file", t.toString());
        }
    }
}
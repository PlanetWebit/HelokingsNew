package planet.com.helokings.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import planet.com.helokings.Adapter.AchievementAdapter;
import planet.com.helokings.Model.AchievementModel;
import planet.com.helokings.R;
import planet.com.helokings.databinding.FragmentAchievementBinding;


public class AchievementFragment extends Fragment {
    FragmentAchievementBinding achievementBinding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        achievementBinding = FragmentAchievementBinding.inflate(inflater, container, false);
        View root = achievementBinding.getRoot();
        listdata();


        return root;
    }

    private void listdata() {
        AchievementModel[] achievementModels = new AchievementModel[]{
                new AchievementModel("Sign in Expert", R.drawable.mainlogo),
                new AchievementModel("Sign in Expert", R.drawable.mainlogo),
                new AchievementModel("Sign in Expert", R.drawable.mainlogo),
                new AchievementModel("Sign in Expert", R.drawable.mainlogo),
                new AchievementModel("Sign in Expert", R.drawable.mainlogo),
                new AchievementModel("Sign in Expert", R.drawable.mainlogo),
                new AchievementModel("Sign in Expert", R.drawable.mainlogo),
                new AchievementModel("Sign in Expert", R.drawable.mainlogo),
        };
        AchievementAdapter achievementAdapter = new AchievementAdapter(getContext(), achievementModels);
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        achievementBinding.recyclerAchievement.setAdapter(achievementAdapter);
        achievementBinding.recyclerAchievement.setLayoutManager(glm);


    }
}
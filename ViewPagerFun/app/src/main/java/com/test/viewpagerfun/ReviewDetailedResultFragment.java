package com.test.viewpagerfun;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.test.viewpagerfun.databinding.ReviewDetailedResultFragmentBinding;
import com.test.viewpagerfun.listeners.onClick.NextReviewItemListener;
import com.test.viewpagerfun.sm2.Review;
import com.test.viewpagerfun.viewmodel.SharedViewModel;

import java.util.Collections;
import java.util.List;

public class ReviewDetailedResultFragment extends Fragment {

    //make communication between fragments possible
    private SharedViewModel model;
    //view binding of fragment
    private ReviewDetailedResultFragmentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ReviewDetailedResultFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();

        model = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        //Update the UI.
        Review review = model.getMostRecentReview();
        binding.tvQuestion.setText(review.getNote().getPrompt());
        if (review.getScore() < 2) {
            binding.tvAnswerResult.setBackgroundColor(Color.RED);
        } else {
            binding.tvAnswerResult.setBackgroundColor(Color.GREEN);
        }

        /*  when a note fails during review add it on top of the list stack.
            This causes the review to be finished only if all items have passed correctly.
         */
        model.getNotes().observe(getViewLifecycleOwner(), notes -> {
            if(notes.size() > 0)
                notes.remove(0);

            if (review.getScore() < 2)
                notes.add(review.getNote());
                //Below could be a feature...
                //Collections.shuffle(notes);
        });

        //Decides finishing the review, or showing next item in queue.
        NextReviewItemListener nextReviewItemListener = NextReviewItemListener.builder()
                .activity(getActivity())
                .model(model)
                .binding(binding)
                .build();

        binding.btnNextTop.setOnClickListener(nextReviewItemListener);
        binding.btnNextBottom.setOnClickListener(nextReviewItemListener);

    }


    //Fragments outlive their views. clean up any references to the binding class instance in the fragment
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


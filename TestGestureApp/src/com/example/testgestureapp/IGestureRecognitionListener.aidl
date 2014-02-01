package com.example.testgestureapp;

import com.example.testgestureapp.Distribution;

interface IGestureRecognitionListener {
	void onGestureRecognized(in Distribution distribution);

	 void onGestureLearned(String gestureName);

	 void onTrainingSetDeleted(String trainingSet);
} 




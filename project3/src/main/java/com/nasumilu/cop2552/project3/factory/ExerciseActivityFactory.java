package com.nasumilu.cop2552.project3.factory;

import java.util.List;
import java.util.Optional;

import com.nasumilu.cop2552.project3.ExerciseActivity;

public interface ExerciseActivityFactory {

	public Optional<ExerciseActivity> create(List<ExerciseActivity> activities);
	
}

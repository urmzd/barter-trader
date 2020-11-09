package ca.dal.bartertrader.utils.handler.live_data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class TransformedLiveData<LiveDataInputT, LiveDataOutputT> extends MediatorLiveData<LiveDataOutputT> {

    private final List<LiveDataInputT> output = new ArrayList<>();

    public TransformedLiveData(List<LiveData<LiveDataInputT>> dataList, Function<List<LiveDataInputT>, LiveDataOutputT> transformer) {

        IntStream.range(0, dataList.size()).forEach(index -> {
            LiveData<LiveDataInputT> current = dataList.get(index);
            super.addSource(current, data -> {
                if (index >= output.size()) {
                    output.add(data);
                } else {
                    output.set(index, data);
                }

                if (output.size() == dataList.size()) {
                    LiveDataOutputT transformedData = transformer.apply(output);
                   super.setValue(transformedData);
                }
            });

        });
    }
}


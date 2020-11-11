package ca.dal.bartertrader.utils.handler.live_data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class TransformedLiveData<LiveDataInputT, LiveDataOutputT> extends MediatorLiveData<LiveDataOutputT> {

    private final Map<Integer, LiveDataInputT> mapper = new HashMap<>();

    public TransformedLiveData(List<LiveData<LiveDataInputT>> dataList, Function<List<LiveDataInputT>, LiveDataOutputT> transformer) {

        IntStream.range(0, dataList.size()).forEach(index -> {
            LiveData<LiveDataInputT> current = dataList.get(index);
                addSource(current, data -> {
                    mapper.put(index, data);

                    if (mapper.size() == dataList.size()) {
                        LiveDataOutputT transformedData = transformer.apply(new ArrayList<>(mapper.values()));
                        super.setValue(transformedData);
                    }
            });

        });
    }
}


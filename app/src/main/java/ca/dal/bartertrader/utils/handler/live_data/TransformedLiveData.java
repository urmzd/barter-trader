package ca.dal.bartertrader.utils.handler.live_data;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

public class TransformedLiveData<LiveDataInputT, LiveDataOutputT> extends MediatorLiveData<LiveDataOutputT> {

    private final List<LiveDataInputT> values;
    private int sourcesSet = 0;

    public TransformedLiveData(List<LiveData<LiveDataInputT>> dataList, Function<List<LiveDataInputT>, LiveDataOutputT> transformer) {
        values = new ArrayList<>(Collections.nCopies(dataList.size(), null));

        IntStream.range(0, dataList.size()).forEach(index -> {
            LiveData<LiveDataInputT> current = dataList.get(index);
            addSource(current, data -> {
                if (values.get(index) == null) {
                    sourcesSet++;
                }
                values.set(index, data);

                if (sourcesSet == dataList.size()) {
                    LiveDataOutputT transformedData = transformer.apply(new ArrayList<>(values));
                    super.setValue(transformedData);
                }
            });
        });
    }
}


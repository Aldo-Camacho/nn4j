package org.nn4j;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nn4j.layers.Layer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleCache {
    boolean forward;
    long paramCount;
    int layerCount;
    public int currentLayer;
    private final List<String> history = new ArrayList<>();
    private final List<Double> costHistory = new ArrayList<>();
    private final Map<String, INDArray> parameters = new HashMap<>();
    private final Map<String, INDArray> buffers = new HashMap<>();

    public void registerParams(String key, INDArray param) {
        paramCount += param.data().length();
        parameters.put(key, param);
        history.add("Cached Parameters: " + key + " Params: " + param);
    }

    public void registerOperation(String key, INDArray result) {
        buffers.put(key, result);
        history.add("Cached Result: " + key + " Result: " + result);
    }

    public void incrementLayer() {
        currentLayer =  (currentLayer + 1) % layerCount;
    }

    public void registerLayer(Layer layer) {
        history.add("Added Layer: " + layerCount +" Type:" + layer.getClass().getName());
        layerCount += 1;
    }
}

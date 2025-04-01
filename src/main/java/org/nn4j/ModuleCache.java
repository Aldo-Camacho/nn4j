package org.nn4j;

import org.nn4j.layers.Layer;
import org.tensorflow.Operand;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.NdArray;
import org.tensorflow.types.TFloat64;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModuleCache {
    boolean forward;
    long paramCount;
    int layerCount;
    public int currentLayer;
    private Module module;
    private final List<String> history = new ArrayList<>();
    private final List<Double> costHistory = new ArrayList<>();
    private final Map<String, Operand<TFloat64>> parameters = new HashMap<>();
    private final Map<String, Operand<TFloat64>> buffers = new HashMap<>();

    public void registerParams(String key, Operand<TFloat64> param) {
        paramCount += param.size();
        parameters.put(key, param);
        history.add("Cached Parameters: " + key + " Params: " + param);
    }

    public void registerOperation(String key, Operand<TFloat64> result) {
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

    public void registerModule(Module module) {
        history.add("Added Module: " + layerCount +" Type:" + module.getClass().getName());
        this.module = module;
    }
}

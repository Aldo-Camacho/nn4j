package org.nn4j.layers;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nn4j.ModuleCache;

@FunctionalInterface
public interface Layer {
    INDArray apply(INDArray in);
}

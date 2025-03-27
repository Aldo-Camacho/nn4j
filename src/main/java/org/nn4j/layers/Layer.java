package org.nn4j.layers;

import org.nd4j.linalg.api.ndarray.INDArray;

@FunctionalInterface
public interface Layer {
    INDArray apply(INDArray in);
}

package org.nn4j;

import org.nd4j.linalg.api.ndarray.INDArray;

public abstract class Module {
    ModuleCache cache = new ModuleCache();
    abstract INDArray forward(INDArray in);
}

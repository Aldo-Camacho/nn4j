package org.nn4j;

import org.nd4j.linalg.api.ndarray.INDArray;

public abstract class Module {
    private ModuleCache cache = new ModuleCache();
    protected abstract INDArray forward(INDArray in);

    public void registerParam(String key, INDArray param) {
        cache.registerParams(key, param);
    }
}

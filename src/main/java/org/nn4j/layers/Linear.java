package org.nn4j.layers;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nn4j.ModuleCache;
import org.nn4j.ModuleCacheFactory;

public class Linear extends AbstractLayer {
    int[] dims = new int[2];
    INDArray weight;
    INDArray bias;

    public Linear(int inFeatures, int outFeatures) {
        super();
        dims[0] = inFeatures;
        dims[1] = outFeatures;
        weight = Nd4j.rand(outFeatures, inFeatures);
        bias = Nd4j.zeros(outFeatures);
    }

    @Override
    public INDArray apply(INDArray in) {
        INDArray res = Nd4j.matmul(weight, in).add(bias);
        register(res);
        return res;
    }
}

package org.nn4j.layers;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.api.ops.impl.scalar.RectifiedLinear;
import org.nd4j.linalg.factory.Nd4j;

public class Relu extends AbstractLayer {

    public Relu() {
        super();
    }

    @Override
    public INDArray apply(INDArray in) {
        INDArray res = Nd4j.exec(new RectifiedLinear(in));
        register(res);
        return res;
    }
}

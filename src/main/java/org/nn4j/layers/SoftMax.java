package org.nn4j.layers;


import org.tensorflow.Operand;
import org.tensorflow.Tensor;
import org.tensorflow.types.TFloat64;

public class SoftMax extends AbstractLayer {

    @Override
    public Operand<TFloat64> apply(Operand<TFloat64> in) {
        Operand<TFloat64> a = Nd4j.exec(new Exp(in));
        Operand<TFloat64> sum = Nd4j.exec(new Sum(in));
        return null;
    }
}

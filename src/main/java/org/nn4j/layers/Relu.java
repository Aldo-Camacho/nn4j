package org.nn4j.layers;


import org.tensorflow.Operand;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.NdArray;
import org.tensorflow.types.TFloat64;

public class Relu extends AbstractLayer {

    public Relu() {
        super();
    }

    @Override
    public Operand<TFloat64> apply(Operand<TFloat64> in) {
        Operand<TFloat64> res = tf.nn.relu(in);
        register(res);
        return res;
    }
}

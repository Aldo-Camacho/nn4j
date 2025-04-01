package org.nn4j.layers;

import org.tensorflow.Operand;
import org.tensorflow.types.TFloat64;

import java.util.Random;

public class Linear extends AbstractLayer {
    int[] dims = new int[2];
    Operand<TFloat64> weight;
    Operand<TFloat64> bias;

    public Linear(int inFeatures, int outFeatures) {
        super();
        dims[0] = inFeatures;
        dims[1] = outFeatures;

        weight = tf.random.randomUniform(tf.constant(new int[] { outFeatures, inFeatures }), TFloat64.class);
        bias = tf.zeros(tf.constant((new int[] {outFeatures})), TFloat64.class);
    }

    @Override
    public Operand<TFloat64> apply(Operand<TFloat64> in) {
        Operand<TFloat64> res = tf.math.add(tf.linalg.matMul(weight, in), bias);
        register(res);
        return res;
    }
}

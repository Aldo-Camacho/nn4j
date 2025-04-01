package org.nn4j.layers;

import org.tensorflow.Operand;
import org.tensorflow.Tensor;
import org.tensorflow.ndarray.NdArray;
import org.tensorflow.types.TFloat64;

@FunctionalInterface
public interface Layer {
    Operand<TFloat64> apply(Operand<TFloat64> in);
}

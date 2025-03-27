package org.nn4j;

import org.nd4j.linalg.api.ndarray.INDArray;

import java.util.HashMap;
import java.util.Map;

public class ModuleCache {
    Map<String, INDArray> parameters = new HashMap<>();
    Map<String, INDArray> buffers = new HashMap<>();
}

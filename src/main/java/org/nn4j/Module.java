package org.nn4j;


import org.tensorflow.Operand;
import org.tensorflow.types.TFloat64;

public abstract class Module {
    private final ModuleCache cache = ModuleCacheFactory.getCache();
    protected abstract Operand<TFloat64> forward(Operand<TFloat64> in);

    public Module() {
        cache.registerModule(this);
    }
}

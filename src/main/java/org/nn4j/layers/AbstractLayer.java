package org.nn4j.layers;

import org.nn4j.ModuleCache;
import org.nn4j.ModuleCacheFactory;
import org.tensorflow.Operand;
import org.tensorflow.op.Ops;
import org.tensorflow.types.TFloat64;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractLayer implements Layer {
    protected Ops tf = Ops.create();
    protected ModuleCache cache = ModuleCacheFactory.getCache();

    protected AbstractLayer() {
        cache.registerLayer(this);
    }

    protected void register(Operand<TFloat64> res) {
        cache.registerOperation("A" + cache.currentLayer, res);
        cache.incrementLayer();

    }
}

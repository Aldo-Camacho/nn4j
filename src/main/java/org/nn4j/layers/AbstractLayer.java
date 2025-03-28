package org.nn4j.layers;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nn4j.ModuleCache;
import org.nn4j.ModuleCacheFactory;

import javax.swing.table.AbstractTableModel;

public abstract class AbstractLayer implements Layer {
    ModuleCache cache = ModuleCacheFactory.getCache();

    protected AbstractLayer() {
        cache.registerLayer(this);
    }

    protected void register(INDArray res) {
        cache.registerOperation("A" + cache.currentLayer, res);
        cache.incrementLayer();
    }
}

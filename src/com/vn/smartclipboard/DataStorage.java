package com.vn.smartclipboard;

import com.intellij.ide.util.PropertiesComponent;
import org.apache.commons.lang.ArrayUtils;

import java.util.concurrent.atomic.AtomicInteger;

class DataStorage {
    private static final String PLUGIN_UNIQUE_KEY = "org.vn.Copy";
    private static final PropertiesComponent propertiesComponentInstance = PropertiesComponent.getInstance();
    private static int MAX_SIZE = 10;
    private static AtomicInteger SIZE;

    static {
        SIZE = new AtomicInteger(keyExists(PLUGIN_UNIQUE_KEY) ? propertiesComponentInstance.getValues(modifyKey(PLUGIN_UNIQUE_KEY)).length : 0);
    }

    static boolean storeData(String key, String value) {
        if (allSlotsTaken()) {
            return false;
        }
        String modifiedKey = modifyKey(key);
        if (propertiesComponentInstance.isValueSet(modifiedKey)) {
            return false;
        }
        SIZE.incrementAndGet();
        propertiesComponentInstance.setValue(modifiedKey, value);
        propertiesComponentInstance.setValues(modifyKey(PLUGIN_UNIQUE_KEY), keyExists(PLUGIN_UNIQUE_KEY) ? (String[]) ArrayUtils.addAll(propertiesComponentInstance.getValues(modifyKey(PLUGIN_UNIQUE_KEY)), new String[]{key}) : new String[]{key});
        return true;
    }
    

    static boolean keyExists(String key) {
        return propertiesComponentInstance.isValueSet(modifyKey(key));
    }

    static String retrieveAndRemoveData(String key) {
        String modifiedKey = modifyKey(key);
        String value = propertiesComponentInstance.getValue(modifiedKey);
        if (value != null) {
            propertiesComponentInstance.unsetValue(modifiedKey);
            SIZE.decrementAndGet();
        }
        return value;
    }

    static boolean allSlotsTaken() {
        return SIZE.intValue() == MAX_SIZE;
    }

    private static String modifyKey(String key) {
        return PLUGIN_UNIQUE_KEY + key;
    }

}

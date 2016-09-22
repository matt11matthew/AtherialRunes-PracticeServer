package net.atherialrunes.practiceserver.api.handler.handlers.vendor;

import net.atherialrunes.practiceserver.api.handler.ListenerHandler;

import java.util.HashMap;

/**
 * Created by Matthew E on 9/22/2016.
 */
public class VendorHandler extends ListenerHandler {

    public static HashMap<String, Vendor> vendors = new HashMap<>();

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onUnload() {

    }

    public static void registerVendor(Vendor vendor) {
        vendors.put(vendor.getNPCName(), vendor);
    }
}

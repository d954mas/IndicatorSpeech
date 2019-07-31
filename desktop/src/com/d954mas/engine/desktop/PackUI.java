package com.d954mas.engine.desktop;

import com.d954mas.assetpacker.packer.Packer;
import com.d954mas.assetpacker.resource.BundlesCreator;

import java.io.File;
import java.io.IOException;

public class PackUI {
    public static void main(String[] args) throws IOException {
        Packer packer=new Packer();
        //delete all assets in ./android/assets
        packer.clearAndroidAssets();
        //pack all assets form ./assets
        packer.packAssets(new File("assets"));
        BundlesCreator creator=new BundlesCreator();
        creator.create(new File("android/assets"));
    }
}
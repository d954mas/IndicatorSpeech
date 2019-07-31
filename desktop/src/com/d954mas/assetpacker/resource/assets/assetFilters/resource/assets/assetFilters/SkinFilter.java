package com.d954mas.assetpacker.resource.assets.assetFilters.resource.assets.assetFilters;

import com.d954mas.assetpacker.resource.assets.assetFilters.resource.assets.assetTypes.Asset;
import com.d954mas.assetpacker.resource.assets.assetFilters.resource.assets.assetTypes.AtlasAsset;
import com.d954mas.assetpacker.resource.assets.assetFilters.resource.assets.assetTypes.ResourceBundleAsset;
import com.d954mas.assetpacker.resource.assets.assetFilters.resource.assets.assetTypes.SkinAsset;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class SkinFilter implements AssetFilter {
    @Override
    public boolean isAsset(File file) {
        return file.getName().startsWith("uiskin") && FilenameUtils.getExtension(file.getPath()).equals("json");
    }

    @Override
    public Asset getAsset(File file) {
        return new SkinAsset(file);
    }
}

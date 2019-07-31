package com.d954mas.assetpacker.resource.assets.assetFilters.resource.assets.assetTypes;


import com.d954mas.assetpacker.resource.assets.assetFilters.Cs;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FileHandleAsset extends Asset {
    public FileHandleAsset(File file) {
        super(file);
    }

    @Override
    public String getAssetClass() {
        return "FileHandle";
    }

    @Override
    public List<String> getConstructor() {
        return Cs.of(getAssetName() + " = " + "Gdx.files.internal(\"" + file.getPath().replace("\\", "/").replace("android/assets/", "") + "\");");
    }

    @Override
    public String getAssetName() {
        String name = file.getName();
        name= Character.toLowerCase(name.charAt(0))+name.substring(1);
        int pos = name.lastIndexOf(".");
        if (pos > 0) {
            name = name.substring(0, pos) + "_" + name.substring(pos+1);
        }
        return name;
    }

    @Override
    public Set<String> getImports() {
        Set<String> imports=new HashSet<String>();
        imports.addAll(super.getImports());
        imports.add("import com.badlogic.gdx.Gdx;");
        imports.add("import com.badlogic.gdx.files.FileHandle;");
        return imports;
    }

    @Override
    public boolean needDestructor() {
        return false;
    }

    @Override
    public boolean needUnload() {
        return true;
    }

    @Override
    public List<String> getLoadDestructor() {
        return super.getDestructor();
    }
}

package com.ww.classloaderdemo;

import android.util.Log;

import java.io.File;
import java.io.IOException;

import dalvik.system.DexFile;

public class MyClassLoader extends ClassLoader {

    private static final String TAG = "MyClassLoader";
    private String mDexOutputPath;
    private String mDexPath;
    private String mLibPath;

    protected File[] mFiles;
    protected DexFile[] mDexs;
    private boolean mInitialized;

    public MyClassLoader(String dexPath, String dexOutputDir, String libPath,
                         ClassLoader parent) {
        super(parent);
        if (dexPath == null)
            throw new RuntimeException("dexPath为null");
        mDexPath = dexPath;
        mDexOutputPath = dexOutputDir;
        mLibPath = libPath;
        ensureInit();
    }

    @Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
        Class<?> clazz = null;
        try {
            clazz = getParent().loadClass(className);
        } catch (ClassNotFoundException e) {
            Log.d(TAG, "loadClass: 没找着自己来加载");
        }

        if (clazz != null) {
            return clazz;
        }

        try {
            clazz = loadClassBySelf(className);
            if (clazz != null) {
                return clazz;
            }
        } catch (ClassNotFoundException ignored) {

        }
        throw new ClassNotFoundException(className + " in loader " + this);
    }


    public Class<?> loadClassBySelf(String className) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(className);

        if (clazz == null) {
            clazz = findClass(className);
        }

        return clazz;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        ensureInit();
        Class clazz;
        int length = mFiles.length;
        for (int i = 0; i < length; i++) {

            if (mDexs[i] != null) {
                String slashName = name.replace('.', '/');
                clazz = mDexs[i].loadClass(slashName, this);
                if (clazz != null) {
                    return clazz;
                }
            }
        }
        throw new ClassNotFoundException(name + " in loader " + this);
    }

    protected synchronized void ensureInit() {
        if (mInitialized) {
            return;
        }

        String[] dexPathList;

        mInitialized = true;

        dexPathList = mDexPath.split(":");
        int length = dexPathList.length;

        mFiles = new File[length];
        mDexs = new DexFile[length];

        for (int i = 0; i < length; i++) {
            File pathFile = new File(dexPathList[i]);
            mFiles[i] = pathFile;

            if (pathFile.isFile()) {
                try {
                    mDexs[i] = DexFile.loadDex(dexPathList[i], dexPathList[i], 0);
                } catch (IOException e) {
                    Log.e(TAG, "ensureInit: ", e);
                }
            }
        }
    }
}

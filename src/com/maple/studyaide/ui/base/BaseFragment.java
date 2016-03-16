package com.maple.studyaide.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment基类
 * 
 * @author shaoshuai
 * 
 */

public abstract class BaseFragment extends Fragment {
	public View view;
	public Context mContext;
	public FragmentManager fm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mContext = getActivity();
		this.fm = getFragmentManager();
	}

	/** 构建UI */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = initView(inflater);
		view.setClickable(true);// 防止点击穿透，底层的fragment响应上层点击触摸事件
		return view;
	}

	/** 数据填充UI的操作 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		initData(savedInstanceState);
		initListener();
		super.onActivityCreated(savedInstanceState);
	}

	/** onCreateView方法中构建UI(将xml转换成view对象) */
	public abstract View initView(LayoutInflater inflater);

	/** onActivityCreated方法中请求网络。返回数据填充UI */
	public abstract void initData(Bundle savedInstanceState);
	
	/** onActivityCreated方法处理监听 */
	public abstract void initListener();

	
}

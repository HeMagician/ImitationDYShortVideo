<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/bg_gray"
    android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/white"
        android:orientation="vertical">

        <com.zhouwei.mzbanner.MZBannerView
            android:id="@+id/vip_buy_banner"
            android:layout_width="match_parent"
            android:layout_height="170dp"
            app:canLoop="false"
            app:middle_page_cover="true"
            app:open_mz_mode="true" />

        <TextView
            android:id="@+id/vip_bannel_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="20dp"
            android:layout_marginTop="@dimen/dp_10"
            android:layout_marginRight="20dp"
            android:layout_marginBottom="16dp"
            android:gravity="center"
            android:textColor="@color/font_gray"
            android:textSize="10sp" />
    </LinearLayout>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="10dp"
        android:background="@color/white"
        android:orientation="vertical">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:gravity="center"
            android:text="成为会员"
            android:textColor="@color/font_gray"
            android:textSize="14sp"
            android:textStyle="bold" />

        <View
            android:layout_width="match_parent"
            android:layout_height="1dp"
            android:layout_marginLeft="40dp"
            android:layout_marginRight="40dp"
            android:background="@color/line_color" />

        <LinearLayout
            android:id="@+id/vip_buy_select_city"
            android:layout_width="match_parent"
            android:layout_height="64dp"
            android:layout_marginLeft="15dp"
            android:layout_marginTop="15dp"
            android:layout_marginRight="15dp"
            android:layout_marginBottom="15dp"
            android:background="@drawable/shape_vip_bg"
            android:gravity="center_vertical"
            android:orientation="horizontal">

            <ImageView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="15dp"
                android:src="@mipmap/vip_city" />

            <TextView
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginLeft="10dp"
                android:layout_weight="1"
                android:gravity="left"
                android:text="选择城市"
                android:textColor="@color/font_gray"
                android:textSize="13sp" />

            <TextView
                android:id="@+id/vip_buy_city_name"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginRight="10dp"
                android:gravity="center"
                android:textColor="@color/font_gray"
                android:textSize="14sp"
                android:textStyle="bold" />

            <ImageView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginRight="15dp"
                android:src="@mipmap/vip_arrow" />

        </LinearLayout>

        <LinearLayout
            android:id="@+id/vip_buy_select_grade"
            android:layout_width="match_parent"
            android:layout_height="64dp"
            android:layout_marginLeft="15dp"
            android:layout_marginTop="15dp"
            android:layout_marginRight="15dp"
            android:layout_marginBottom="15dp"
            android:background="@drawable/shape_vip_bg"
            android:gravity="center_vertical"
            android:orientation="horizontal">

            <ImageView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="15dp"
                android:src="@mipmap/vip_user" />

            <TextView
                android:layout_width="0dp"
                android:layout_height="wrap_content"
                android:layout_marginLeft="10dp"
                android:layout_weight="1"
                android:gravity="left"
                android:text="会员类型"
                android:textColor="@color/font_gray"
                android:textSize="13sp" />

            <ImageView
                android:id="@+id/vip_buy_select_grade_img"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
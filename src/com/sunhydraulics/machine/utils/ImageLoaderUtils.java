package com.sunhydraulics.machine.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sunhydraulics.machine.view.ImageProgress;

/**
 * @author maoweiwei
 * 
 */
public class ImageLoaderUtils {

	public static FileNameGenerator getDefaultFileNameGenerator() {
		return new HashCodeFileNameGenerator();
	}

	public static DisplayImageOptions getDisplayImageOptions(
			boolean cacheInMemeory, boolean cacheOnDisc,
			BitmapDisplayer displayer) {
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.resetViewBeforeLoading(true).cacheInMemory(cacheInMemeory)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheOnDisc(cacheOnDisc).bitmapConfig(Bitmap.Config.RGB_565);
		if (null != displayer) {
			builder.displayer(displayer);
		}
		DisplayImageOptions opts = builder.build();
		return opts;
	}

	/**
	 * @param url
	 * @param imageView
	 * @param displayer
	 * @param imageLoadingListener
	 */
	public static void displayPic(String url, ImageProgress imageView,
			BitmapDisplayer displayer, ImageLoadingListener imageLoadingListener) {
		displayPic(url, imageView, true, true, true, Bitmap.Config.RGB_565,
				displayer, imageLoadingListener,
				ImageScaleType.IN_SAMPLE_POWER_OF_2);
	}

	/**
	 * @param url
	 * @param imageView
	 * @param imageLoadingListener
	 */
	public static void displayPic(String url, ImageProgress imageView,
			ImageLoadingListener imageLoadingListener) {
		displayPic(url, imageView, true, true, true, Bitmap.Config.RGB_565,
				null, imageLoadingListener, ImageScaleType.IN_SAMPLE_POWER_OF_2);
	}

	public static void displayPic(String url, ImageProgress imageView,
			boolean cacheInMemeory, boolean cacheOnDisc,
			BitmapDisplayer displayer, ImageLoadingListener imageLoadingListener) {
		displayPic(url, imageView, cacheInMemeory, cacheOnDisc, true,
				Bitmap.Config.RGB_565, displayer, imageLoadingListener,
				ImageScaleType.IN_SAMPLE_POWER_OF_2);
	}

	public static void displayPic(String url, ImageProgress imageView,
			boolean cacheInMemeory, boolean cacheOnDisc,
			BitmapDisplayer displayer) {
		displayPic(url, imageView, cacheInMemeory, cacheOnDisc,
				Bitmap.Config.RGB_565, displayer);
	}

	public static void displayPic(String url, ImageProgress imageView,
			boolean cacheInMemeory, boolean cacheOnDisc, Bitmap.Config config,
			BitmapDisplayer displayer) {
		displayPic(url, imageView, cacheInMemeory, cacheOnDisc, true, config,
				displayer);
	}

	public static void displayPic(String url, final ImageProgress imageView,
			boolean cacheInMemeory, boolean cacheOnDisc,
			boolean resetViewBeforeLoading, Bitmap.Config config,
			BitmapDisplayer displayer) {
		displayPic(url, imageView, cacheInMemeory, cacheOnDisc,
				resetViewBeforeLoading, config, displayer, null);
	}

	public static void displayPic(String url, final ImageProgress imageView,
			boolean cacheInMemeory, boolean cacheOnDisc,
			boolean resetViewBeforeLoading, Bitmap.Config config,
			BitmapDisplayer displayer,
			final ImageLoadingListener imageLoadingListener) {
		displayPic(url, imageView, cacheInMemeory, cacheOnDisc,
				resetViewBeforeLoading, config, displayer,
				imageLoadingListener, ImageScaleType.IN_SAMPLE_POWER_OF_2);
	}

	public static void displayPic(String url, final ImageProgress imageView,
			boolean cacheInMemeory, boolean cacheOnDisc,
			boolean resetViewBeforeLoading, Bitmap.Config config,
			BitmapDisplayer displayer,
			final ImageLoadingListener imageLoadingListener,
			ImageScaleType scaleType) {

		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.imageScaleType(scaleType).cacheInMemory(cacheInMemeory)
				.cacheOnDisc(true).bitmapConfig(config)
				.resetViewBeforeLoading(resetViewBeforeLoading);

		if (null != displayer) {
			builder.displayer(displayer);
		}
		DisplayImageOptions opts = builder.build();
		ImageLoader.getInstance().displayImage(getRealUri(url),
				imageView.getImageView(), opts,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {
						imageView.getImageView().setVisibility(View.VISIBLE);
						if (null != imageView.getProgressBar()) {
							imageView.getProgressBar().setVisibility(View.GONE);
						}
						if (null != imageLoadingListener) {
							imageLoadingListener.onLoadingComplete(imageUri,
									view, loadedImage);
						}
					}

					@Override
					public void onLoadingStarted(String imageUri, View view) {
						super.onLoadingStarted(imageUri, view);
						imageView.getImageView().setVisibility(View.GONE);
						if (null != imageView.getProgressBar()) {
							imageView.getProgressBar().setVisibility(
									View.VISIBLE);
							if (imageView.getProgressBar() instanceof ProgressBar) {
								((ProgressBar) imageView.getProgressBar())
										.setProgress(0);
							}
						}
						if (null != imageLoadingListener) {
							imageLoadingListener.onLoadingStarted(imageUri,
									view);
						}
					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						super.onLoadingCancelled(imageUri, view);
						if (null != imageLoadingListener) {
							imageLoadingListener.onLoadingCancelled(imageUri,
									view);
						}
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						super.onLoadingFailed(imageUri, view, failReason);
						imageView.getImageView().setVisibility(View.VISIBLE);
						if (null != imageLoadingListener) {
							imageLoadingListener.onLoadingFailed(imageUri,
									view, failReason);
						}
					}
				}, new ImageLoadingProgressListener() {

					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {

						if (imageView.getProgressBar() instanceof ProgressBar) {
							ProgressBar pb = (ProgressBar) imageView
									.getProgressBar();
							if (null != pb
									&& (current - pb.getProgress() > total / 15)) { // avoid
								// to
								// many
								pb.setMax(total);
								pb.setProgress(current);
							}
						}
					}
				});
	}

	public static void displayPic(String url, ImageView imageView) {
		displayPic(url, imageView, true, true, true, Bitmap.Config.RGB_565,
				null, null);
	}

	public static void displayPic(String url, ImageView imageView,
			ImageLoadingListener imageLoadingListener) {
		displayPic(url, imageView, true, true, true, Bitmap.Config.RGB_565,
				null, imageLoadingListener);
	}

	public static void displayPic(String url, ImageView imageView,
			boolean cacheInMemeory, boolean cacheOnDisc,
			BitmapDisplayer displayer) {
		displayPic(url, imageView, cacheInMemeory, cacheOnDisc,
				Bitmap.Config.RGB_565, displayer);
	}

	public static void displayPic(String url, ImageView imageView,
			boolean cacheInMemeory, boolean cacheOnDisc, int imageStub,
			BitmapDisplayer displayer) {
		displayPic(url, imageView, cacheInMemeory, cacheOnDisc, imageStub,
				true, Bitmap.Config.RGB_565, displayer, null);
	}

	public static void displayPic(String url, ImageView imageView,
			boolean cacheInMemeory, boolean cacheOnDisc, Bitmap.Config config,
			BitmapDisplayer displayer) {
		displayPic(url, imageView, cacheInMemeory, cacheOnDisc, true, config,
				displayer);
	}

	public static void displayPic(String url, ImageView imageView,
			boolean cacheInMemeory, boolean cacheOnDisc,
			boolean resetViewBeforeLoading, Bitmap.Config config,
			BitmapDisplayer displayer) {
		displayPic(url, imageView, cacheInMemeory, cacheOnDisc,
				resetViewBeforeLoading, config, displayer, null);
	}

	public static void displayPic(String url, ImageView imageView,
			boolean cacheInMemeory, boolean cacheOnDisc,
			boolean resetViewBeforeLoading, Bitmap.Config config,
			BitmapDisplayer displayer, ImageLoadingListener imageLoadingListener) {
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.cacheInMemory(cacheInMemeory).cacheOnDisc(cacheOnDisc)
				.bitmapConfig(config)
				.resetViewBeforeLoading(resetViewBeforeLoading);
		if (null != displayer) {
			builder.displayer(displayer);
		}
		DisplayImageOptions opts = builder.build();
		ImageLoader.getInstance().displayImage(getRealUri(url), imageView,
				opts, imageLoadingListener);
	}

	public static void displayPic(String url, final ImageView imageView,
			boolean cacheInMemeory, boolean cacheOnDisc, int imageStub,
			boolean resetViewBeforeLoading, Bitmap.Config config,
			BitmapDisplayer displayer,
			final ImageLoadingListener imageLoadingListener) {

		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.showImageForEmptyUri(imageStub).showImageOnFail(imageStub)
				.cacheInMemory(cacheInMemeory).cacheOnDisc(cacheOnDisc)
				.bitmapConfig(config).showImageOnLoading(imageStub)
				.resetViewBeforeLoading(resetViewBeforeLoading);

		if (null != displayer) {
			builder.displayer(displayer);
		}
		DisplayImageOptions opts = builder.build();

		ImageLoader.getInstance().displayImage(getRealUri(url), imageView,
				opts, imageLoadingListener);
	}

	public static void displayPic(String url, ImageView imageView,
			boolean cacheInMemeory, boolean cacheOnDisc,
			boolean resetViewBeforeLoading, Bitmap.Config config,
			BitmapDisplayer displayer,
			ImageLoadingListener imageLoadingListener,
			ImageLoadingProgressListener progressListener) {
		DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.cacheInMemory(cacheInMemeory).cacheOnDisc(cacheOnDisc)
				.bitmapConfig(config)
				.resetViewBeforeLoading(resetViewBeforeLoading);
		if (null != displayer) {
			builder.displayer(displayer);
		}
		DisplayImageOptions opts = builder.build();
		ImageLoader.getInstance().displayImage(getRealUri(url), imageView,
				opts, imageLoadingListener, progressListener);
	}

	public static String getRealUri(String url) {
		if (null == url)
			return null;

		String localfilePath = url;

		if (localfilePath.startsWith("http://")
				|| localfilePath.startsWith("https://")
				|| localfilePath.startsWith("file://")) {
			return localfilePath;
		} else {
			return "file://" + localfilePath;
		}
	}

	public static boolean isHttpPath(String url) {
		if (null == url)
			return false;
		return url.startsWith("http://") || url.startsWith("https://");
	}

}

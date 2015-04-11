package com.sunhydraulics.machine.fragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.sunhydraulics.machine.R;
import com.sunhydraulics.machine.view.RatioImageView;

@EFragment(R.layout.layout_feedback_view)
public class FeedbackFragment extends Fragment {

	@ViewById(R.id.ratioImageView)
	public RatioImageView ratioImageView;

	@ViewById(R.id.detailView)
	public TextView detailView;

	@AfterViews
	void init() {

		Bitmap rawBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.logo);
		ratioImageView.setImageBitmap(rawBitmap);
		ratioImageView.setWHRatio(3.3866f);

		StringBuilder sb = new StringBuilder();
		sb.append("    SUN HYDAULICS成立于1970年，总部位于美国佛罗里达州萨拉索塔（Sarasota，Florida），");
		sb.append("并在英国，德国，法国，中国，韩国，印度设有阀块工厂或者办事处。");
		sb.append("SUN公司1997年在美国纳斯达克上市，股票代码为SNHY。公司始终致力于螺纹插装阀行业，40年的历史为公司赢得了巨大的声誉。");
		sb.append("\n");
		sb.append("   SUN作为螺纹插装阀的领军生产商，以独特的浮动孔型结构，在大流量，高压力的场合有着非常广阔的应用。SUN插装阀除卓越的性能使其深受市场青睐以外，全球稳定的经销网络，灵活的货期（标准货期21天出厂）供应将大大地满足不同客户的要求。");
		sb.append("\n");
		sb.append("    如您对SUN的产品和服务有任何问题，请联系：");
		sb.append("\n");
		sb.append("021-51162862");
		sb.append("\n");
		sb.append("SunChinaInfo@sunhydraulics.com");
		sb.append("\n");

		detailView.setText(sb.toString());

	}
}

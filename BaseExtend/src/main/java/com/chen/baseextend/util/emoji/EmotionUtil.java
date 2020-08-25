package com.chen.baseextend.util.emoji;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.widget.EditText;

import androidx.collection.ArrayMap;

import com.chen.baseextend.R;


/**
 * Created by chen on 2016/10/11.
 */

public class EmotionUtil {

    public static ArrayMap<String, Integer> EMOTION_CLASSIC_MAP;

    static {

        EMOTION_CLASSIC_MAP = new ArrayMap<>();

        EMOTION_CLASSIC_MAP.put("[爱你]", R.drawable.d_aini);
        EMOTION_CLASSIC_MAP.put("[奥特曼]", R.drawable.d_aoteman);
        EMOTION_CLASSIC_MAP.put("[拜拜]", R.drawable.d_baibai);
        EMOTION_CLASSIC_MAP.put("[悲伤]", R.drawable.d_beishang);
        EMOTION_CLASSIC_MAP.put("[鄙视]", R.drawable.d_bishi);
        EMOTION_CLASSIC_MAP.put("[闭嘴]", R.drawable.d_bizui);
        EMOTION_CLASSIC_MAP.put("[馋嘴]", R.drawable.d_chanzui);
        EMOTION_CLASSIC_MAP.put("[吃惊]", R.drawable.d_chijing);
        EMOTION_CLASSIC_MAP.put("[哈欠]", R.drawable.d_dahaqi);
        EMOTION_CLASSIC_MAP.put("[打脸]", R.drawable.d_dalian);
        EMOTION_CLASSIC_MAP.put("[顶]", R.drawable.d_ding);
        EMOTION_CLASSIC_MAP.put("[doge]", R.drawable.d_doge);
        EMOTION_CLASSIC_MAP.put("[肥皂]", R.drawable.d_feizao);
        EMOTION_CLASSIC_MAP.put("[感冒]", R.drawable.d_ganmao);
        EMOTION_CLASSIC_MAP.put("[鼓掌]", R.drawable.d_guzhang);
        EMOTION_CLASSIC_MAP.put("[哈哈]", R.drawable.d_haha);
        EMOTION_CLASSIC_MAP.put("[害羞]", R.drawable.d_haixiu);
        EMOTION_CLASSIC_MAP.put("[汗]", R.drawable.d_han);
        EMOTION_CLASSIC_MAP.put("[微笑]", R.drawable.d_hehe);
        EMOTION_CLASSIC_MAP.put("[黑线]", R.drawable.d_heixian);
        EMOTION_CLASSIC_MAP.put("[哼]", R.drawable.d_heng);
        EMOTION_CLASSIC_MAP.put("[色]", R.drawable.d_huaxin);
        EMOTION_CLASSIC_MAP.put("[挤眼]", R.drawable.d_jiyan);
        EMOTION_CLASSIC_MAP.put("[可爱]", R.drawable.d_keai);
        EMOTION_CLASSIC_MAP.put("[可怜]", R.drawable.d_kelian);
        EMOTION_CLASSIC_MAP.put("[酷]", R.drawable.d_ku);
        EMOTION_CLASSIC_MAP.put("[困]", R.drawable.d_kun);
        EMOTION_CLASSIC_MAP.put("[白眼]", R.drawable.d_landelini);
        EMOTION_CLASSIC_MAP.put("[泪]", R.drawable.d_lei);
        EMOTION_CLASSIC_MAP.put("[马到成功]", R.drawable.d_madaochenggong);
        EMOTION_CLASSIC_MAP.put("[喵喵]", R.drawable.d_miao);
        EMOTION_CLASSIC_MAP.put("[男孩儿]", R.drawable.d_nanhaier);
        EMOTION_CLASSIC_MAP.put("[怒]", R.drawable.d_nu);
        EMOTION_CLASSIC_MAP.put("[怒骂]", R.drawable.d_numa);
        EMOTION_CLASSIC_MAP.put("[女孩儿]", R.drawable.d_nvhaier);
        EMOTION_CLASSIC_MAP.put("[钱]", R.drawable.d_qian);
        EMOTION_CLASSIC_MAP.put("[亲亲]", R.drawable.d_qinqin);
        EMOTION_CLASSIC_MAP.put("[傻眼]", R.drawable.d_shayan);
        EMOTION_CLASSIC_MAP.put("[生病]", R.drawable.d_shengbing);
        EMOTION_CLASSIC_MAP.put("[草泥马]", R.drawable.d_shenshou);
        EMOTION_CLASSIC_MAP.put("[失望]", R.drawable.d_shiwang);
        EMOTION_CLASSIC_MAP.put("[衰]", R.drawable.d_shuai);
        EMOTION_CLASSIC_MAP.put("[睡]", R.drawable.d_shuijiao);
        EMOTION_CLASSIC_MAP.put("[思考]", R.drawable.d_sikao);
        EMOTION_CLASSIC_MAP.put("[太开心]", R.drawable.d_taikaixin);
        EMOTION_CLASSIC_MAP.put("[偷笑]", R.drawable.d_touxiao);
        EMOTION_CLASSIC_MAP.put("[吐]", R.drawable.d_tu);
        EMOTION_CLASSIC_MAP.put("[兔子]", R.drawable.d_tuzi);
        EMOTION_CLASSIC_MAP.put("[挖鼻]", R.drawable.d_wabishi);
        EMOTION_CLASSIC_MAP.put("[委屈]", R.drawable.d_weiqu);
        EMOTION_CLASSIC_MAP.put("[笑cry]", R.drawable.d_xiaoku);
        EMOTION_CLASSIC_MAP.put("[熊猫]", R.drawable.d_xiongmao);
        EMOTION_CLASSIC_MAP.put("[嘻嘻]", R.drawable.d_xixi);
        EMOTION_CLASSIC_MAP.put("[嘘]", R.drawable.d_xu);
        EMOTION_CLASSIC_MAP.put("[阴险]", R.drawable.d_yinxian);
        EMOTION_CLASSIC_MAP.put("[疑问]", R.drawable.d_yiwen);
        EMOTION_CLASSIC_MAP.put("[右哼哼]", R.drawable.d_youhengheng);
        EMOTION_CLASSIC_MAP.put("[晕]", R.drawable.d_yun);
        EMOTION_CLASSIC_MAP.put("[炸鸡啤酒]", R.drawable.d_zhajipijiu);
        EMOTION_CLASSIC_MAP.put("[抓狂]", R.drawable.d_zhuakuang);
        EMOTION_CLASSIC_MAP.put("[猪头]", R.drawable.d_zhutou);
        EMOTION_CLASSIC_MAP.put("[最右]", R.drawable.d_zuiyou);
        EMOTION_CLASSIC_MAP.put("[左哼哼]", R.drawable.d_zuohengheng);
        EMOTION_CLASSIC_MAP.put("[给力]", R.drawable.f_geili);
        EMOTION_CLASSIC_MAP.put("[互粉]", R.drawable.f_hufen);
        EMOTION_CLASSIC_MAP.put("[囧]", R.drawable.f_jiong);
        EMOTION_CLASSIC_MAP.put("[萌]", R.drawable.f_meng);
        EMOTION_CLASSIC_MAP.put("[神马]", R.drawable.f_shenma);
        EMOTION_CLASSIC_MAP.put("[威武]", R.drawable.f_v5);
        EMOTION_CLASSIC_MAP.put("[喜]", R.drawable.f_xi);
        EMOTION_CLASSIC_MAP.put("[织]", R.drawable.f_zhi);
        EMOTION_CLASSIC_MAP.put("[NO]", R.drawable.h_buyao);
        EMOTION_CLASSIC_MAP.put("[good]", R.drawable.h_good);
        EMOTION_CLASSIC_MAP.put("[haha]", R.drawable.h_haha);
        EMOTION_CLASSIC_MAP.put("[来]", R.drawable.h_lai);
        EMOTION_CLASSIC_MAP.put("[OK]", R.drawable.h_ok);
        EMOTION_CLASSIC_MAP.put("[拳头]", R.drawable.h_quantou);
        EMOTION_CLASSIC_MAP.put("[弱]", R.drawable.h_ruo);
        EMOTION_CLASSIC_MAP.put("[握手]", R.drawable.h_woshou);
        EMOTION_CLASSIC_MAP.put("[耶]", R.drawable.h_ye);
        EMOTION_CLASSIC_MAP.put("[赞]", R.drawable.h_zan);
        EMOTION_CLASSIC_MAP.put("[作揖]", R.drawable.h_zuoyi);
        EMOTION_CLASSIC_MAP.put("[伤心]", R.drawable.l_shangxin);
        EMOTION_CLASSIC_MAP.put("[心]", R.drawable.l_xin);
        EMOTION_CLASSIC_MAP.put("[蛋糕]", R.drawable.o_dangao);
        EMOTION_CLASSIC_MAP.put("[飞机]", R.drawable.o_feiji);
        EMOTION_CLASSIC_MAP.put("[干杯]", R.drawable.o_ganbei);
        EMOTION_CLASSIC_MAP.put("[话筒]", R.drawable.o_huatong);
        EMOTION_CLASSIC_MAP.put("[蜡烛]", R.drawable.o_lazhu);
        EMOTION_CLASSIC_MAP.put("[礼物]", R.drawable.o_liwu);
        EMOTION_CLASSIC_MAP.put("[绿丝带]", R.drawable.o_lvsidai);
        EMOTION_CLASSIC_MAP.put("[围脖]", R.drawable.o_weibo);
        EMOTION_CLASSIC_MAP.put("[围观]", R.drawable.o_weiguan);
        EMOTION_CLASSIC_MAP.put("[音乐]", R.drawable.o_yinyue);
        EMOTION_CLASSIC_MAP.put("[照相机]", R.drawable.o_zhaoxiangji);
        EMOTION_CLASSIC_MAP.put("[钟]", R.drawable.o_zhong);
        EMOTION_CLASSIC_MAP.put("[浮云]", R.drawable.w_fuyun);
        EMOTION_CLASSIC_MAP.put("[沙尘暴]", R.drawable.w_shachenbao);
        EMOTION_CLASSIC_MAP.put("[太阳]", R.drawable.w_taiyang);
        EMOTION_CLASSIC_MAP.put("[微风]", R.drawable.w_weifeng);
        EMOTION_CLASSIC_MAP.put("[鲜花]", R.drawable.w_xianhua);
        EMOTION_CLASSIC_MAP.put("[下雨]", R.drawable.w_xiayu);
        EMOTION_CLASSIC_MAP.put("[月亮]", R.drawable.w_yueliang);
    }


    public static void addEmojiToEditText(Context context, EditText editText, String emojiName, int resId) {

        SpannableStringBuilder sb = new SpannableStringBuilder(emojiName);

        sb.setSpan(new ImageSpan(context, getEmojiBitmap(context, resId, 70)),
                0, emojiName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        int index = editText.getSelectionStart();

        Editable editable = editText.getEditableText();

        if (index < 0) {
            editable.append(sb);
        } else {
            editable.insert(index, sb);
        }

    }

    public static Bitmap getEmojiBitmap(Context context, int resId, int size) {

        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(context.getResources(), resId, options);

        Bitmap source = BitmapFactory.decodeResource(context.getResources(), resId);

        Matrix matrix = new Matrix();

        if (size > 0) {
            float scale = (size + 0f) / options.outWidth;
            matrix.postScale(scale, scale); //长和宽放大缩小的比例
        }

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getWidth(), matrix, true);
    }
}

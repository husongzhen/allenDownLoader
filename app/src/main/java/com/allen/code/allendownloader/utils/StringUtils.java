package com.allen.code.allendownloader.utils;

import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.widget.TextView;

/**
 * 
 * 
 * 文字
 * 
 * @author husongzhen
 * 
 */

public class StringUtils {

	public static final float stringWidth(TextView v, String s) {
		TextPaint paint = v.getPaint();
		return paint.measureText(s);
	}

	// 计算s的字节长度
	public static int length(String s) {
		if (s == null)
			return 0;
		char[] c = s.toCharArray();
		int len = 0;
		for (int i = 0; i < c.length; i++) {
			len++;
			if (!isLetter(c[i])) {
				len++;
			}
		}
		return len;
	}

	// 截取指定长度
	public static String subStringByByte(String s, int len) {
		String result = "";
		if (s == null)
			return result;
		char[] c = s.toCharArray();
		int resultLen = 0;
		for (int i = 0; i < c.length; i++) {
			resultLen++;
			if (!isLetter(c[i])) {
				resultLen++;
			}
			if (resultLen > len) {
				break;
			}
			result += c[i];
		}
		return result;
	}

	// 是不是英文字符
	public static boolean isLetter(char c) {
		int k = 0x80;
		return c / k == 0 ? true : false;
	}

	// 自动加上省略号
	public static final String ellipsizingText(String fullText, int maxLines,
                                               TextPaint paint, int textWidth) {
		String ELLIPSIS = "...";
		String workingText = fullText;
		if (maxLines != -1) {
			Layout layout = createWorkingLayout(workingText, paint, textWidth);
			if (layout.getLineCount() > maxLines) {
				workingText = fullText.substring(0,
						layout.getLineEnd(maxLines - 1)).trim();
				while (createWorkingLayout(workingText + ELLIPSIS, paint,
						textWidth).getLineCount() > maxLines) {
					int lastSpace = workingText.lastIndexOf(' ');
					if (lastSpace == -1) {
						break;
					}
					workingText = workingText.substring(0, lastSpace);
				}
				workingText = workingText + ELLIPSIS;
			}
		}

		return workingText;
	}

	private static Layout createWorkingLayout(String workingText,
                                              TextPaint paint, int textWidth) {
		float lineSpacingMultiplier = 1.0f;
		float lineAdditionalVerticalPadding = 0.0f;
		return new StaticLayout(workingText, paint, textWidth,
				Alignment.ALIGN_NORMAL, lineSpacingMultiplier,
				lineAdditionalVerticalPadding, false);
	}
}

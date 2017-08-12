package com.nha.pelsdreams.screens.transitions;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

/**
 * Controla el paso de una pantalla de niveles o sueños a otra
 * 
 * @author Nacho Herrera
 *
 */
public class PagedScrollPane extends ScrollPane {
	
	private boolean wasPanDragFling = false;
	private Table content;

	public PagedScrollPane(Actor widget, Skin skin) {
		super(widget, skin);
		setup();
	}

	public PagedScrollPane(Actor widget, ScrollPaneStyle style) {
		super(null, style);
		setup();
	}

	private void setup() {
		content = new Table();
		super.setWidget(content);
	}

	public void addPages(Actor[] pages) {
		for (Actor page : pages) {
			content.add(page);
		}
	}

	public void addPage(Actor page) {
		content.add(page);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (wasPanDragFling && !isPanning() && !isDragging() && !isFlinging()) {
			wasPanDragFling = false;
			scrollToPage();
		} else {
			if (isPanning() || isDragging() || isFlinging()) {
				wasPanDragFling = true;
			}
		}
	}

	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		if (content != null) {
			for (Cell<?> cell : content.getCells()) {
				cell.width(width);
			}
			content.invalidate();
		}
	}

	public void setPageSpacing(float pageSpacing) {
		if (content != null) {
			content.defaults().space(pageSpacing);
			for (Cell<?> cell : content.getCells()) {
				cell.space(pageSpacing);
			}
			content.invalidate();
		}
	}

	private void scrollToPage() {
		final float scrollX = getScrollX();
		final float maxX = getMaxX();

		if (scrollX >= maxX || scrollX <= 0)
			return;

		Array<Actor> pages = content.getChildren();
		float pageX = 0;
		float pageWidth = 0;
		if (pages.size > 0) {
			for (Actor a : pages) {
				pageX = a.getX();
				pageWidth = a.getWidth();
				if (scrollX < (pageX + pageWidth * 0.5)) {
					break;
				}
			}
			setScrollX(MathUtils
					.clamp(pageX, 0, maxX));
		}
	}

}

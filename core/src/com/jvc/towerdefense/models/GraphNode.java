package com.jvc.towerdefense.models;

import com.badlogic.gdx.utils.Array;

public class GraphNode {
	public int nodeType;
	/* 1 -> Tower */
	/* Towers and Links are indexed */
	public int index;
	public Array<Integer> links;
	
	public GraphNode(int nodeType, int index) {
		this.nodeType = nodeType;
		this.index = index;
		this.links = new Array<Integer>();
	}
	public int getNodeType() {
		return nodeType;
	}
	public int getIndex() {
		return index;
	}
	public void addLink(int linkIndex) {
		links.add(linkIndex);
	}
}

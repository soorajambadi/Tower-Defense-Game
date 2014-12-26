package com.jvc.towerdefense.manager;

import java.util.LinkedList;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jvc.towerdefense.models.Link;
import com.jvc.towerdefense.models.Tower;
import com.jvc.towerdefense.models.TownCentre;
import com.jvc.towerdefense.models.World;


public class GraphManager {
	/**
	Array<GraphNode> nodes = new Array<GraphNode>();
	**/
	private World world;
	public GraphManager(World world) {
		this.world = world;
	}
	/*
	public boolean insertNode(int nodeType, int index) {
		/* I don't know if this is a good approach **/
		/*nodes.add(new GraphNode(nodeType, index));
		return true;
		}
		/* Fix this what happens if insert Failed */
	
	
	
	//public boolean insertLinktoNode(int nodeType, int index, int linkIndex) {
	/* it is assumed that given node is a tower */
	//	nodes.get(getNodebyIndexandType(nodeType, index)).addLink(linkIndex);
	//	return true;
	//}
	
	/*public int getNodebyIndexandType(int nodeType, int index) {
		for(GraphNode node: nodes) {
			if(node.getNodeType() == nodeType && node.getIndex() == index)
				return nodes.indexOf(node, true);
				/* I don't know what is identity */
		//}
		//return -1;
	//}
	
	/* This is a wrapper function change it as needed */ 
	public boolean doesPathExist(Tower t1, Tower t2) {
		/*
		Array<Link> allLinks = world.getLinks();
		Array<Link> t1Links = t1.getLinks(allLinks);
		for(Link link : t1Links) {
			if(link.t1 == t2 || link.t2 == t2) {
				return true;
			}
		}
		return false;
		*/
		return breadthFirstSearch(t1, t2);
	}
	
	public boolean breadthFirstSearch(Tower t1, Tower t2) {
		LinkedList<Tower> q = new LinkedList<Tower>();
		//Array<Link> oLinks = t1.getLinks(world.getLinks());
		Array<Link> oLinks = t1.getTowerLinks();
		Array<Boolean> visited = new Array<Boolean>();
		for(Tower t: world.getTowers()) 
			visited.add(false);
		visited.set(world.getTowers().indexOf(t1, false), true);
		for(Link l : oLinks) {
			if(l.t1 == t1.name)
				q.addLast(InstanceManager.getInstance().getTowerByName(l.t2));
			else if(l.t2 == t1.name)
				q.addLast(InstanceManager.getInstance().getTowerByName(l.t1));
		}
		try {
		Tower popped = q.pop();
		while(popped != null) {
			if(popped == t2)
				return true;
			if(!visited.get(world.getTowers().indexOf(popped, false))) {
				//oLinks = popped.getLinks(world.getLinks());
				oLinks = popped.getTowerLinks();
				for(Link l : oLinks) {
					if(l.t1 == popped.name)
						q.addLast(InstanceManager.getInstance().getTowerByName(l.t2));
					else if(l.t2 == popped.name)
						q.addLast(InstanceManager.getInstance().getTowerByName(l.t1));
				}
				visited.set(world.getTowers().indexOf(popped, false), true);
			}
			popped = q.pop();
		}
		return false;
	}
		catch(Exception e) {
			return false;
		}
	}
	
	public Array<Link> getAllLinksTower(Tower tower) {
		//Array<Link> allLinks = world.getLinks();
		//return tower.getLinks(allLinks);
		return tower.getTowerLinks();
	}
	
	public float getTotalSteam(TownCentre townCenter) {
		Array<Link> tClinks = getAllLinksTower(townCenter);
		Array<Tower> tTowers = world.getTowers();
		float producedSteam = 0f;
		for(int i=0; i<tTowers.size;i++) {
			if(doesPathExist(tTowers.get(i), townCenter)) {
				producedSteam+=tTowers.get(i).productionRate;
			}
		}
		/*
		for(Link link : tClinks) {
			if(link.t1.UID!=-1) {
				producedSteam+=link.t1.productionRate;
			}
			else if(link.t2.UID!=-1)
				producedSteam+=link.t2.productionRate;
		}
		*/
		return producedSteam;
	}
	
	public boolean satisfyCosts(int t1, int t2) {
		float totalSteam = world.getTownCentre().getTotalSteam();
		if(t1 == -1 && t2 == -1)
    		return false; // This means both points are in Town Centre
    	else if(t1 == -1)
    		return satisfyCost(world.getTownCentre(), world.getTowers().get(t2), totalSteam);
    	else if(t2 == -1)
    		return satisfyCost(world.getTownCentre(), world.getTowers().get(t1), totalSteam);
    	else
    		return satisfyCost(world.getTownCentre(), world.getTowers().get(t2), totalSteam);
	}
	
	public boolean satisfyCost(Tower t1, Tower t2, float totalSteam) {
		Vector2 len = new Vector2(t1.getPosition()).sub(t2.getPosition());
		if(len.len2() <= totalSteam)
			return true;
		return false;
	}

	public float getCosts(int t1, int t2) {
		if(t1 == -1 && t2 == -1)
    		return 0; // This means both points are in Town Centre
    	else if(t1 == -1)
    		return getCostOfPipe(world.getTownCentre(), world.getTowers().get(t2));
    	else if(t2 == -1)
    		return getCostOfPipe(world.getTownCentre(), world.getTowers().get(t1));
    	else
    		return getCostOfPipe(world.getTownCentre(), world.getTowers().get(t2));
	}
	
	public float getCostOfPipe(Tower t1, Tower t2) {	
		return new Vector2(t1.getPosition()).sub(t2.getPosition()).len2();
	}
	
	public void update(float delta) {
		TownCentre tCentre = world.getTownCentre();
		tCentre.addProducedSteam(getTotalSteam(tCentre)*delta);
		tCentre.consumeSteam(tCentre.getSteamConsumedforLevel()*delta);
	}
}

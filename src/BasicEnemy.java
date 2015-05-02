// As created by Bastiaan Wuisman on 2-5-2015
// Created using IntelliJ IDEA


public class BasicEnemy extends Enemy{

	BasicEnemy( MainScript mainScriptSet, int xSet, int ySet ) {
		super(mainScriptSet, xSet, ySet);
	}

	@Override
	void move() {
		if( mainScript.player.x > x ) x++;
		else if( mainScript.player.x < x ) x--;

		if( mainScript.player.y > y ) y++;
		else if( mainScript.player.y < y ) y--;
	}
}

package wingman.modifiers.weapons;

import wingman.game.*;

public class NullWeapon extends AbstractWeapon {
	@Override
	public void fireWeapon(Ship theShip) {
		return;
	}

	@Override
	public void read(Object theObject) {		
	}

}

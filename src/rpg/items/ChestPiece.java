package rpg.items;

public class ChestPiece extends Equipment {

    public ChestPiece(double[] stats) {
        super(stats);
    }

    @Override
    public EquipmentType getType() {
        return EquipmentType.CHEST_PIECE;
    }
}

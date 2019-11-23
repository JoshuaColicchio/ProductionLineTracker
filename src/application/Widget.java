package application;

/**
 * Class that used to test functionality.
 *
 * @deprecated Use {@link AudioPlayer} or {@link MoviePlayer} instead.
 * @author Joshua Colicchio
 */
class Widget extends Product {

  Widget(int id, String name, String manu, ItemType type) {
    super(id, name, manu, type);
  }
}

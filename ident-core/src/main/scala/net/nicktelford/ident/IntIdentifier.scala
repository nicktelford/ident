package net.nicktelford.ident

/** An Identifier that's represented as a 4-byte signed integer (Int) */
case class IntIdentifier(value: Int) extends Identifier[IntIdentifier] {
  val bytes = value.toString.getBytes
  val asString = value.toString
  override val length = 4
}

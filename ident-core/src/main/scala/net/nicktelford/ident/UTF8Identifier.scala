package net.nicktelford.ident

/** An Identifier that's represented as an arbitrary UTF-8 String */
case class UTF8Identifier(value: String) extends Identifier[UTF8Identifier] {
  val bytes = value.getBytes("UTF-8")
  val asString = value
  override val length = value.length
}

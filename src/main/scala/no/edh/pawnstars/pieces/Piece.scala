package no.edh.pawnstars.pieces

import akka.actor.{Actor, UntypedActor}
import no.edh.pawnstars.game.Square

trait Piece extends Actor {
  val color : Color
  val square : Square
}

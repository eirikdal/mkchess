package no.edh.pawnstars.game

import akka.actor.{ActorRef, Props, ActorSystem}
import akka.pattern.Patterns
import akka.util.Timeout
import no.edh.pawnstars.pieces.{Piece, Pawn}

import scala.collection.immutable.HashMap
import scala.concurrent.{Await, Future}
import scala.concurrent.duration.Duration

class Board(board: Map[Square, Option[ActorRef]]) {
  def move(s: String) : Board = {
    val pattern = """^(?i)([PRNBQK])?([A-H][1-8])?([A-H][1-8])"""
    val matcher = pattern.r
    val matcher(p, s, d) = s

    new Board(new HashMap[Square, Option[ActorRef]])
  }


  def -(piece: Piece): Board = {
    new Board(HashMap())
  }

  def printBoard() = {
    println("\n\n\n      A  B  C  D  E  F  G  H")
    println("      ----------------------")
    for (i <- 0 to 7) {
      print(" " + (i + 1).toString + " | ")
      for (j <- 0 to 7) {
        board(Square(i, j)) match {
          case Some(a: ActorRef) =>
            print(" " + a.path.name.charAt(0) + " ")
          case None => print(" . ")
        }
      }
      if (i < 8) println("| \n")
    }
    println("      ----------------------")
  }

  def get() = board

  def pieces() = board.filter(_._2.isInstanceOf[Some[ActorRef]])

}

case class Square(rank: Int, file: Int)

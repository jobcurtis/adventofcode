package com.emlett.aoc.y2019

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class Year2019Test : FunSpec({

    context("Day 01") {
        test("Part 1") {
            Day01.part1() shouldBe 3282386
        }

        test("Part 2") {
            Day01.part2() shouldBe 4920708
        }
    }

    context("Day 02") {
        test("Part 1") {
            Day02.part1() shouldBe 6327510
        }

        test("Part 2") {
            Day02.part2() shouldBe 4112
        }
    }

    context("Day 03") {
        test("Part 1") {
            Day03.part1() shouldBe 860
        }

        test("Part 2") {
            Day03.part2() shouldBe 9238
        }
    }

    context("Day 04") {
        test("Part 1") {
            Day04.part1() shouldBe 2090
        }

        test("Part 2") {
            Day04.part2() shouldBe 1419
        }
    }

    context("Day 05") {
        test("Part 1") {
            Day05.part1() shouldBe 12428642
        }

        test("Part 2") {
            Day05.part2() shouldBe 918655
        }
    }

    context("Day 06") {
        test("Part 1") {
            Day06.part1() shouldBe 300598
        }

        test("Part 2") {
            Day06.part2() shouldBe 520
        }
    }

    context("Day 07") {
        test("Part 1") {
            Day07.part1() shouldBe 92663
        }

        test("Part 2") {
            Day07.part2() shouldBe 14365052
        }
    }

    context("Day 08") {
        test("Part 1") {
            Day08.part1() shouldBe 828
        }

        test("Part 2") {
            Day08.part2() shouldBe "Output: \n" + """
                ████ █    ███    ██ ████ 
                   █ █    █  █    █ █    
                  █  █    ███     █ ███  
                 █   █    █  █    █ █    
                █    █    █  █ █  █ █    
                ████ ████ ███   ██  █    
                """.trimIndent() + '\n'
        }
    }

    context("Day 09") {
        test("Part 1") {
            Day09.part1() shouldBe 3518157894
        }

        test("Part 2") {
            Day09.part2() shouldBe 80379
        }
    }

})
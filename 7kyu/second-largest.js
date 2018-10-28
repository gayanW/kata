/**
 * return second largest number in an given array, without sorting
 * https://www.codewars.com/kata/second-largest-in-array
 */
function secondLargest(array) {
  if (array == null || !(array instanceof Array)) return undefined;

  var highest, secondHighest;

  for (var i = 0; i < array.length; i++) {
    if (isNaN(array[i])) continue;
    num = parseInt(array[i]);

    if (highest == undefined || num > highest) {
      secondHighest = highest;
      highest = num;
    }
    if (num < highest && (secondHighest == undefined || num > secondHighest))
      secondHighest = num;
  }
  return secondHighest;
}
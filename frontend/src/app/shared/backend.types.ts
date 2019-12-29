export interface SearchResponse {
  foods: FoodSearchResponse[];
}
export interface FoodSearchResponse {
  ndbno: string;
  name: string;
  unit: string;
  amount: number;
}
export interface FoodResponse {
  name: string, amount: string, unit: string, measurements: FoodMeasurement[], message: string;
}

export interface FoodMeasurement {
  ounces: string, grams: string, description: string, nutrition: NutritionReport;
}

export interface NutritionReport {
  calories: string, calorieRating: number, carbs: string, fat: string, protein: string, alcohol: string,
  carbsPoC: string, fatPoC: string, proteinPoC: string, alcoholPoC: string, fiber: string;
}

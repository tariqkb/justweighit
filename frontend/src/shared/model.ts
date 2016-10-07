export interface SearchResponse {
  foods: FoodSearchResponse[];
}
export interface FoodSearchResponse {
  ndbno: string;
  name: string;
}

/* tslint:disable:max-line-length */
/**
 * App with angular GIU and springboot backend and swagger description for REST
 * 1.0
 * Swagger stock app
 * localhost:9091
 */

export interface StockData {
  actual?: boolean;
  /** format: float */
  change?: number;
  /** format: float */
  changePercent?: number;
  /** format: float */
  close?: number;
  /** format: date-time */
  date?: string;
  /** format: float */
  high?: number;
  /** format: int64 */
  id?: number;
  /** format: float */
  low?: number;
  /** format: float */
  open?: number;
  symbol?: string;
}

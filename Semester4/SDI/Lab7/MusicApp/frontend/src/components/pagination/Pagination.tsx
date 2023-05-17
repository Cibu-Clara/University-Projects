import { Button } from "@mui/material";
import { debounce } from "lodash";
import React from "react";
import { useCallback, useEffect, useMemo } from "react";

export interface PaginatorProps {
  rowsPerPage: number;
  totalRows: number;
  currentPage: number;
  isFirstPage: boolean;
  isLastPage: boolean;
  setPage: (page: number) => void;
  goToNextPage: () => void;
  goToPrevPage: () => void;
}

export const Paginator = ({
  rowsPerPage,
  totalRows,
  currentPage,
  setPage,
}: PaginatorProps) => {
  const totalPages = Math.ceil(totalRows / rowsPerPage);
  const pageRange = 11;

  const changeCurrentPage = (pageNumber: number) => {
    if (pageNumber < 1) {
      setPage(1);

      return;
    } else if (pageNumber > totalPages) {
      setPage(totalPages);
      return;
    }

    setPage(pageNumber);
  };

  const debounceOnChange = useCallback(debounce(changeCurrentPage, 500), []);

  useEffect(() => {
    return () => {
      debounceOnChange.cancel();
    };
  }, [debounceOnChange]);

  const visiblePages = useMemo(() => {
    const pageNumbers = [];

    debugger;
    if (totalPages <= pageRange) {
      for (let i = 1; i <= totalPages; i++) {
        pageNumbers.push(i);
      }
    } else {
      let start = Math.max(currentPage - Math.floor(pageRange / 2), 1);
      let end = Math.min(start + pageRange - 1, totalPages);

      if (end - start < pageRange - 1) {
        start = Math.max(end - pageRange + 1, 1);
      }

      for (let i = start; i <= end; i++) {
        pageNumbers.push(i);
      }

      if (start - 5 > 1 && start - 5 >= 1) {
        pageNumbers.unshift("...");
      }
      for (let i = Math.min(start-1, 5); i >= 1; --i) {
        pageNumbers.unshift(i);
      }


      if (end < totalPages-4) {
        pageNumbers.push("...");
        // pageNumbers.push(totalPages-4);
        // pageNumbers.push(totalPages-3);
        // pageNumbers.push(totalPages-2);
        // pageNumbers.push(totalPages-1);
        // pageNumbers.push(totalPages);
      }

      for (let i = totalPages-4; i <= Math.max(end, totalPages); ++i) {
        pageNumbers.push(i);
      }

    }

    return pageNumbers;
  }, [totalPages, currentPage]);

  return (
    <div className="pagination">
      {/* <button
        className="floating"
        disabled={isFirstPage}
        onClick={() => setPage(1)}
      >
        1
      </button> */}

      {visiblePages.map((page, index) => (
        <React.Fragment key={index}>

            <Button
              variant={page === currentPage ? "contained" : "outlined"}
              style={{backgroundColor: "#C8BEEA", color:"#72648B"}}
              disabled={page==="..."}
              onClick={() => setPage(Number(page))}
            >
              {page}
            </Button>

        </React.Fragment>
      ))}

      {/* <button
        className="floating"
        disabled={isLastPage}
        onClick={() => setPage(totalPages)}
      >
        {totalPages}
      </button> */}
    </div>
  );
};
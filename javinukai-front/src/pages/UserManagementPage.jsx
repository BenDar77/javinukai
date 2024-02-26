import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import getUsers from "../services/users/getUsers";
import PaginationSettings from "../Components/user-management/PaginationSettings";
import { UserListItem } from "../Components/user-management/UserListItem";
import { BarLoader } from "react-spinners";
import { useTranslation } from "react-i18next";

const defaultPagination = {
  page: 0,
  limit: 10,
  sortBy: "surname",
  sortDesc: "true",
  surnameContains: null,
};

export default function UserManagementPage() {
  const [paginationSettings, setPaginationSettings] =
    useState(defaultPagination);
  const { data, isFetching } = useQuery({
    queryKey: [
      "users",
      paginationSettings.page,
      paginationSettings.limit,
      paginationSettings.sortBy,
      paginationSettings.sortDesc,
      paginationSettings.surnameContains,
    ],
    queryFn: () =>
      getUsers(
        paginationSettings.page,
        paginationSettings.limit,
        paginationSettings.sortBy,
        paginationSettings.sortDesc,
        paginationSettings.surnameContains
      ),
  });

  const { t } = useTranslation();
  
  return (
    <div className="w-full min-h-[82vh] lg:flex lg:flex-col lg:items-center bg-slate-50">
      <div className="lg:w-3/4 w-full px-2">
        <PaginationSettings
          pagination={paginationSettings}
          setPagination={setPaginationSettings}
          availablePageNumber={data?.totalPages}
        />
        <div className="hidden lg:grid lg:grid-cols-6 px-3 py-5 font-bold text-lg text-slate-700 bg-white mt-2 rounded-md shadow">
          <p>{t('PaginationSettings.fieldName')}</p>
          <p>{t('PaginationSettings.fieldSurname')}</p>
          <p>{t('PaginationSettings.fieldEmail')}</p>
          <p>{t('PaginationSettings.fieldRole')}</p>
          <p>{t('PaginationSettings.fieldLimits')}</p>
          <p>{t('PaginationSettings.fieldIsEnabled')}</p>
        </div>
        {isFetching ? (
          <div className="h-[50vh] flex flex-col justify-center items-center">
            <BarLoader />
          </div>
        ) : (
          <>
            {data?.content.map((user) => (
              <UserListItem key={user.uuid} userInfo={user} />
            ))}
          </>
        )}
      </div>
    </div>
  );
}
